package application.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.spring.model.DBUser;

public interface DBUserRepository extends JpaRepository<DBUser, Integer>{
    public DBUser findByUsername(String username);
}
