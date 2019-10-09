import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HillcodeAppSharedModule } from 'app/shared/shared.module';
import { CommentaireComponent } from './commentaire.component';
import { CommentaireDetailComponent } from './commentaire-detail.component';
import { CommentaireUpdateComponent } from './commentaire-update.component';
import { CommentaireDeletePopupComponent, CommentaireDeleteDialogComponent } from './commentaire-delete-dialog.component';
import { commentaireRoute, commentairePopupRoute } from './commentaire.route';

const ENTITY_STATES = [...commentaireRoute, ...commentairePopupRoute];

@NgModule({
  imports: [HillcodeAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CommentaireComponent,
    CommentaireDetailComponent,
    CommentaireUpdateComponent,
    CommentaireDeleteDialogComponent,
    CommentaireDeletePopupComponent
  ],
  entryComponents: [CommentaireDeleteDialogComponent]
})
export class HillcodeAppCommentaireModule {}
