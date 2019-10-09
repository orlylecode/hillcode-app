import { Moment } from 'moment';
import { ICommentaire } from 'app/shared/model/commentaire.model';

export interface IArticle {
  id?: number;
  cheminFichier?: string;
  auteur?: string;
  dateCreation?: Moment;
  dateDerniereModif?: Moment;
  description?: string;
  jaime?: number;
  jaimepas?: number;
  partage?: number;
  commentaires?: ICommentaire[];
}

export class Article implements IArticle {
  constructor(
    public id?: number,
    public cheminFichier?: string,
    public auteur?: string,
    public dateCreation?: Moment,
    public dateDerniereModif?: Moment,
    public description?: string,
    public jaime?: number,
    public jaimepas?: number,
    public partage?: number,
    public commentaires?: ICommentaire[]
  ) {}
}
