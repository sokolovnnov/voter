package ru.antisida.voter.repo.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.antisida.voter.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepo extends JpaRepository<Vote, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Vote v WHERE v.id =:id")
    int deleteById(@Param("id") int id);

    @Query("SELECT v FROM  Vote v WHERE v.isActive = true AND v.localDateTime >= :startDate AND v.localDateTime < :endDate ")
    List<Vote> findAllActiveByDate(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate);

    List<Vote> findAllByUserId(int userId);


    @Query("SELECT v FROM Vote v WHERE v.isActive = true AND v.user.id =:userId AND v.localDateTime >= :startDate" +
           " AND v.localDateTime <:endDate")
    List<Vote> findAllActiveByUserIdAndDate(@Param("userId") int userId,
                                            @Param("startDate") LocalDateTime startDate,
                                            @Param("endDate") LocalDateTime endDate);


}
