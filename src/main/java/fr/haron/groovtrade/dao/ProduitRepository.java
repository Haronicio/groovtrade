package fr.haron.groovtrade.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import fr.haron.groovtrade.entities.Produit;


public interface ProduitRepository extends JpaRepository<Produit, Long> {

    public List<Produit> findByDescriptionLike(String string);
    public List<Produit> findByMetaAlbumLike(String string);
    public List<Produit> findByMetaNomLike(String string);
    public List<Produit> findByMetaArtisteLike(String string);
    public List<Produit> findByMetaAnneeLike(String string);
    public List<Produit> findByMetaGenresLike(String string);
    public List<Produit> findByUtilisateurId(Long uid);


    //Recherche global : recherche keyword dans la description, le titre , l'album, le genre, artiste et année
    @Query("SELECT p FROM Produit p WHERE " +
       "p.description LIKE :keyword OR " +
       "p.meta.nom LIKE :keyword OR " +
       "p.meta.album LIKE :keyword OR " +
       "p.meta.genres LIKE :keyword OR " +
       "p.meta.artiste LIKE :keyword OR " +
       "p.meta.annee LIKE :keyword AND " +
       "p.archived = false")
    List<Produit> findGlobal(@Param("keyword") String keyword);

    //Recherche avancée (par critère combiné) : recherche dans chaque champ renseigner de la recherche avancé (voir vue)
    
    @Query("SELECT p FROM Produit p WHERE " +
       "(p.description LIKE %:keyword% OR :keyword IS NULL) AND " +
       "(p.meta.artiste LIKE %:artiste% OR :artiste IS NULL) AND " +
       "(p.meta.nom LIKE %:nom% OR :nom IS NULL) AND " +
       "(p.meta.album LIKE %:album% OR :album IS NULL) AND " +
       "(p.meta.genres LIKE %:genres% OR :genres IS NULL) AND " +
       "((p.meta.annee BETWEEN :annee_inf AND :annee_sup) OR (:annee_inf IS NULL OR :annee_sup IS NULL)) AND "+
       "p.archived = false")
    List<Produit> findByCombinedCriteria(@Param("keyword") String keyword,
                                     @Param("artiste") String artiste,
                                     @Param("nom") String nom,
                                     @Param("album") String album,
                                     @Param("genres") String genres,
                                     @Param("annee_inf") Integer annee_inf,
                                     @Param("annee_sup") Integer annee_sup);



    //Pas besoin d'implémenter manuellement deleteByID, elle est déjà fournie par JpaRepository
    // Pas besoin d'implémenter manuellement save(), elle est déjà fournie par JpaRepository
}
