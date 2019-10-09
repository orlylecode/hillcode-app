import { Component, OnInit } from '@angular/core';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IUtilisateur, Utilisateur } from 'app/shared/model/utilisateur.model';
import { UtilisateurService } from './utilisateur.service';

@Component({
  selector: 'jhi-utilisateur-update',
  templateUrl: './utilisateur-update.component.html'
})
export class UtilisateurUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    speudo: [],
    email: []
  });

  constructor(protected utilisateurService: UtilisateurService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ utilisateur }) => {
      this.updateForm(utilisateur);
    });
  }

  updateForm(utilisateur: IUtilisateur) {
    this.editForm.patchValue({
      id: utilisateur.id,
      speudo: utilisateur.speudo,
      email: utilisateur.email
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const utilisateur = this.createFromForm();
    if (utilisateur.id !== undefined) {
      this.subscribeToSaveResponse(this.utilisateurService.update(utilisateur));
    } else {
      this.subscribeToSaveResponse(this.utilisateurService.create(utilisateur));
    }
  }

  private createFromForm(): IUtilisateur {
    return {
      ...new Utilisateur(),
      id: this.editForm.get(['id']).value,
      speudo: this.editForm.get(['speudo']).value,
      email: this.editForm.get(['email']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUtilisateur>>) {
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
