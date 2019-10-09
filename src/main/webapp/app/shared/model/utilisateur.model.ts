import { ICommentaire } from 'app/shared/model/commentaire.model';

export interface IUtilisateur {
  id?: number;
  speudo?: string;
  email?: string;
  commentaires?: ICommentaire[];
}

export class Utilisateur implements IUtilisateur {
  constructor(public id?: number, public speudo?: string, public email?: string, public commentaires?: ICommentaire[]) {}
}
