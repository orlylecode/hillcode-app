import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'article',
        loadChildren: () => import('./article/article.module').then(m => m.HillcodeAppArticleModule)
      },
      {
        path: 'commentaire',
        loadChildren: () => import('./commentaire/commentaire.module').then(m => m.HillcodeAppCommentaireModule)
      },
      {
        path: 'utilisateur',
        loadChildren: () => import('./utilisateur/utilisateur.module').then(m => m.HillcodeAppUtilisateurModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class HillcodeAppEntityModule {}
