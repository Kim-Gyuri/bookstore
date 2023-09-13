package springstudy.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springstudy.bookstore.domain.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 회원 로그인 아이디로 회원찾기
    Optional<User> findByLoginId(String id);

    // 회원 id 번호로 회원 조회
    User findById(long id);

    // 로그인 아이디/비밀번호 정보를 받아 가입된 회원이 맞는지 체크한다.
    boolean existsByLoginIdAndPassword(String id, String password);
}
