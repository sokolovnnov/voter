package ru.antisida.voter.repo.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Vote;

import java.util.List;

@Transactional
public interface CrudVoteRepo extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id =:id AND v.userId =:userId")
    int deleteByIdAndUserId(@Param("id") int id, @Param("userId") int userId);

    List<Vote> findAllByIsActiveTrue();

    List<Vote> findAllByUserId(int userId);

}
