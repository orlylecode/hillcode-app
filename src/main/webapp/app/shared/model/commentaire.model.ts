import { IArticle } from 'app/shared/model/article.model';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';

export interface ICommentaire {
  id?: number;
  dateCreation?: number;
  commentaire?: string;
  article?: IArticle;
  utilisateur?: IUtilisateur;
}

export class Commentaire implements ICommentaire {
  constructor(
    public id?: number,
    public dateCreation?: number,
    public commentaire?: string,
    public article?: IArticle,
    public utilisateur?: IUtilisateur
  ) {}
}
