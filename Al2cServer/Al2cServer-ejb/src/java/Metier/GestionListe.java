package Metier;

import Controllers.ArticleFacade;
import Controllers.EvenementFacade;
import Controllers.LieuFacade;
import Controllers.ListeFacade;
import Controllers.UtilisateurFacade;
import Entities.Article;
import Entities.Evenement;
import Entities.Lieu;
import Entities.Liste;
import Exception.noLieuFoundException;
import Exception.noListeArticleFoundException;
import Exception.notFoundEvenementException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implémentation de la gestion des listes d'articles
 *
 * @author Alexandre Bertrand
 */
@Stateless
public class GestionListe implements IGestionListe {

    @EJB
    private ListeFacade listeFacade;
    
    @EJB
    private EvenementFacade evenementFacade;
    
    @EJB
    private ArticleFacade articleFacade;
    
    @Override
    public void ajouterListe(int idEvenement, List<Article> articles) {
        Evenement e = evenementFacade.find(idEvenement);
        Liste l = new Liste();
        l.setEvenementId(e);
        listeFacade.create(l);
        int idListe = l.getId();
        for (Article a: articles) {
            a.setListeId(l);
            articleFacade.create(a);
        }
    }

    @Override
    public Liste recupererListe(int idEvenement)
            throws noListeArticleFoundException {
        try {
            Evenement e = evenementFacade.find(idEvenement);
            List<Liste> listes = (List<Liste>) e.getListeCollection();
            Liste liste = listes.get(0);
            if (liste == null)
                throw new noListeArticleFoundException();
            return liste;
        } catch (Exception e) {
            throw new noListeArticleFoundException();
        }
    }
    
}
