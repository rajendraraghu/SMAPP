import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SnowpoleSharedModule } from 'app/shared';
import {
  SourceConnectionComponent,
  SourceConnectionDetailComponent,
  SourceConnectionUpdateComponent,
  SourceConnectionDeletePopupComponent,
  SourceConnectionDeleteDialogComponent,
  sourceConnectionRoute,
  sourceConnectionPopupRoute
} from './';
import { JoyrideModule } from 'ngx-joyride';

const ENTITY_STATES = [...sourceConnectionRoute, ...sourceConnectionPopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES), JoyrideModule.forChild()],
  declarations: [
    SourceConnectionComponent,
    SourceConnectionDetailComponent,
    SourceConnectionUpdateComponent,
    SourceConnectionDeleteDialogComponent,
    SourceConnectionDeletePopupComponent
  ],
  entryComponents: [
    SourceConnectionComponent,
    SourceConnectionUpdateComponent,
    SourceConnectionDeleteDialogComponent,
    SourceConnectionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleSourceConnectionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
