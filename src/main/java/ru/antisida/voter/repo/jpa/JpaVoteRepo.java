package ru.antisida.voter.repo.jpa;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.User;
import ru.antisida.voter.model.Vote;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Comparator;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaVoteRepo {

    @PersistenceContext
    EntityManager em;

    public Vote get(int id, int userId) {
        Vote vote = em.find(Vote.class, id);
        return vote != null && vote.getUserId() == userId ? vote : null;
    }

    @Transactional
    public Vote save(Vote vote, int userId) {
//        User user = em.getReference(User.class, userId);
        vote.setUserId(userId);
        Vote lastVote = em.find(Vote.class, getLastByUser(userId).id());
        if (vote.isNew()) {//тут не новых не должно быть все голоса новые
            lastVote.setActive(false);//fixme проверить логику деактивации
            em.merge(lastVote);
            em.persist(vote);
            return vote;
        } else if (get(vote.id(), userId) == null) {
            return null;
        }
        return em.merge(vote);
    }

    @Transactional
    public Vote deactivate(int id, int userId){
        Vote vote = em.getReference(Vote.class, id);
        vote.setActive(false);
        return em.merge(vote);
    }

    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("DELETE FROM Vote v WHERE v.id=:id AND v.userId =:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    public List<Vote> getAll(int userId) {//todo проверка на админа
        return em.createQuery("SELECT v FROM Vote v", Vote.class)
                .getResultList();
    }

    public List<Vote> getAllActive(int userId) {//todo проверка на админа
        return em.createQuery("SELECT v FROM Vote v WHERE v.isActive = TRUE", Vote.class)
                .getResultList();
    }

    public List<Vote> getAllByUser(int userId) {
        return em.createQuery("SELECT v FROM Vote v WHERE v.userId=:userId", Vote.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    public Vote getLastByUser(int userId) {
//        fixme проверить правильность запроса
//        List<Vote> votes=  em.createQuery("SELECT MAX (v.localDateTime) FROM Vote v WHERE v.userId=:userId ORDER BY v" +
//                              ".localDateTime DESC ",
        List<Vote> votes =  em.createQuery(
                "SELECT v FROM Vote v WHERE v.userId=:userId"
//                ORDER BY v.localDateTime DESC "
                , Vote.class)
                .setParameter("userId", userId)
                .getResultList()
                ;
        //.getSingleResult();  не пройдет при отсутствии результата
        return votes.stream()
                .max(Comparator.comparing(vote -> vote.getLocalDateTime()))
                .orElse(null);//fixme хуйня какая-то, может и нет
    }

}
