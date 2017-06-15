package Metier;

import Entities.Article;
import Entities.Liste;
import Exception.noListeArticleFoundException;
import java.util.List;

/**
 * Interface de la gestion des listes d'articles
 *
 * @author Alexandre Bertrand
 */
public interface IGestionListe {
    
    /**
     * Crée la liste d'articles associée à l'évènement
     * @param idEvenement Identifiant de l'évènement
     * @param articles Articles de la liste à ajouter à l'évènement
     */
    public void ajouterListe(int idEvenement, List<Article> articles); 
    
    /**
     * Retourne la liste d'articles de l'évènement passé en paramètres
     * @param idEvenement Identifiant de l'évènement
     * @throws noListeArticleFoundException 
     * @return Retourne la liste d'articles de l'évènement
     */
    public Liste recupererListe(int idEvenement)
            throws noListeArticleFoundException;
    
}