package org.example.springpfa.Repository;

import org.example.springpfa.entities.Demande;
import org.example.springpfa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande,Long> {
    public Demande findByIdDemande(long id);
    public List<Demande> findByUser(User user);
}
