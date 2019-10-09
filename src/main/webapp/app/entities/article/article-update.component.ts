import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { IArticle, Article } from 'app/shared/model/article.model';
import { ArticleService } from './article.service';

@Component({
  selector: 'jhi-article-update',
  templateUrl: './article-update.component.html'
})
export class ArticleUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    cheminFichier: [],
    auteur: [],
    dateCreation: [],
    dateDerniereModif: [],
    description: [],
    jaime: [],
    jaimepas: [],
    partage: []
  });

  constructor(protected articleService: ArticleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ article }) => {
      this.updateForm(article);
    });
  }

  updateForm(article: IArticle) {
    this.editForm.patchValue({
      id: article.id,
      cheminFichier: article.cheminFichier,
      auteur: article.auteur,
      dateCreation: article.dateCreation != null ? article.dateCreation.format(DATE_TIME_FORMAT) : null,
      dateDerniereModif: article.dateDerniereModif != null ? article.dateDerniereModif.format(DATE_TIME_FORMAT) : null,
      description: article.description,
      jaime: article.jaime,
      jaimepas: article.jaimepas,
      partage: article.partage
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const article = this.createFromForm();
    if (article.id !== undefined) {
      this.subscribeToSaveResponse(this.articleService.update(article));
    } else {
      this.subscribeToSaveResponse(this.articleService.create(article));
    }
  }

  private createFromForm(): IArticle {
    return {
      ...new Article(),
      id: this.editForm.get(['id']).value,
      cheminFichier: this.editForm.get(['cheminFichier']).value,
      auteur: this.editForm.get(['auteur']).value,
      dateCreation:
        this.editForm.get(['dateCreation']).value != null ? moment(this.editForm.get(['dateCreation']).value, DATE_TIME_FORMAT) : undefined,
      dateDerniereModif:
        this.editForm.get(['dateDerniereModif']).value != null
          ? moment(this.editForm.get(['dateDerniereModif']).value, DATE_TIME_FORMAT)
          : undefined,
      description: this.editForm.get(['description']).value,
      jaime: this.editForm.get(['jaime']).value,
      jaimepas: this.editForm.get(['jaimepas']).value,
      partage: this.editForm.get(['partage']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticle>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
