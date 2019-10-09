import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommentaire, Commentaire } from 'app/shared/model/commentaire.model';
import { CommentaireService } from './commentaire.service';
import { IArticle } from 'app/shared/model/article.model';
import { ArticleService } from 'app/entities/article/article.service';
import { IUtilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from 'app/entities/utilisateur/utilisateur.service';

@Component({
  selector: 'jhi-commentaire-update',
  templateUrl: './commentaire-update.component.html'
})
export class CommentaireUpdateComponent implements OnInit {
  isSaving: boolean;

  articles: IArticle[];

  utilisateurs: IUtilisateur[];

  editForm = this.fb.group({
    id: [],
    dateCreation: [],
    commentaire: [],
    article: [],
    utilisateur: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected commentaireService: CommentaireService,
    protected articleService: ArticleService,
    protected utilisateurService: UtilisateurService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ commentaire }) => {
      this.updateForm(commentaire);
    });
    this.articleService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IArticle[]>) => mayBeOk.ok),
        map((response: HttpResponse<IArticle[]>) => response.body)
      )
      .subscribe((res: IArticle[]) => (this.articles = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.utilisateurService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUtilisateur[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUtilisateur[]>) => response.body)
      )
      .subscribe((res: IUtilisateur[]) => (this.utilisateurs = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(commentaire: ICommentaire) {
    this.editForm.patchValue({
      id: commentaire.id,
      dateCreation: commentaire.dateCreation,
      commentaire: commentaire.commentaire,
      article: commentaire.article,
      utilisateur: commentaire.utilisateur
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const commentaire = this.createFromForm();
    if (commentaire.id !== undefined) {
      this.subscribeToSaveResponse(this.commentaireService.update(commentaire));
    } else {
      this.subscribeToSaveResponse(this.commentaireService.create(commentaire));
    }
  }

  private createFromForm(): ICommentaire {
    return {
      ...new Commentaire(),
      id: this.editForm.get(['id']).value,
      dateCreation: this.editForm.get(['dateCreation']).value,
      commentaire: this.editForm.get(['commentaire']).value,
      article: this.editForm.get(['article']).value,
      utilisateur: this.editForm.get(['utilisateur']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentaire>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackArticleById(index: number, item: IArticle) {
    return item.id;
  }

  trackUtilisateurById(index: number, item: IUtilisateur) {
    return item.id;
  }
}
