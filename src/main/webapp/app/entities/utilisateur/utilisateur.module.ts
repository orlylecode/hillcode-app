import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HillcodeAppSharedModule } from 'app/shared/shared.module';
import { UtilisateurComponent } from './utilisateur.component';
import { UtilisateurDetailComponent } from './utilisateur-detail.component';
import { UtilisateurUpdateComponent } from './utilisateur-update.component';
import { UtilisateurDeletePopupComponent, UtilisateurDeleteDialogComponent } from './utilisateur-delete-dialog.component';
import { utilisateurRoute, utilisateurPopupRoute } from './utilisateur.route';

const ENTITY_STATES = [...utilisateurRoute, ...utilisateurPopupRoute];

@NgModule({
  imports: [HillcodeAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    UtilisateurComponent,
    UtilisateurDetailComponent,
    UtilisateurUpdateComponent,
    UtilisateurDeleteDialogComponent,
    UtilisateurDeletePopupComponent
  ],
  entryComponents: [UtilisateurDeleteDialogComponent]
})
export class HillcodeAppUtilisateurModule {}
