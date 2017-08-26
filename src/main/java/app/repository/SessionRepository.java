package app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import app.model.Session;

public interface SessionRepository extends JpaRepository<Session, Long>  {

}
