package top.veselushki.repository;

import org.springframework.data.repository.CrudRepository;
import top.veselushki.model.FacebookPost;

public interface FacebookPostRepository extends CrudRepository<FacebookPost, String> {

}
