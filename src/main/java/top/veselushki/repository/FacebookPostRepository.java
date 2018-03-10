package top.veselushki.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import top.veselushki.model.FacebookPost;

import javax.persistence.LockModeType;
import java.util.List;

public interface FacebookPostRepository extends JpaRepository<FacebookPost, String> {

    @Query("FROM FacebookPost WHERE status = 'NEW'")
    List<FacebookPost> findNotSentPosts();

    @Modifying
    @Query("UPDATE FacebookPost SET status = 'SENT' WHERE topicLink in :topicLinks")
    int updateToSent(@Param("topicLinks") List<String> topicLinks);
}
