import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SnowpoleSharedModule } from 'app/shared';
import {
  SnowflakeConnectionComponent,
  SnowflakeConnectionDetailComponent,
  SnowflakeConnectionUpdateComponent,
  SnowflakeConnectionDeletePopupComponent,
  SnowflakeConnectionDeleteDialogComponent,
  snowflakeConnectionRoute,
  snowflakeConnectionPopupRoute
} from './';

const ENTITY_STATES = [...snowflakeConnectionRoute, ...snowflakeConnectionPopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    SnowflakeConnectionComponent,
    SnowflakeConnectionDetailComponent,
    SnowflakeConnectionUpdateComponent,
    SnowflakeConnectionDeleteDialogComponent,
    SnowflakeConnectionDeletePopupComponent
  ],
  entryComponents: [
    SnowflakeConnectionComponent,
    SnowflakeConnectionUpdateComponent,
    SnowflakeConnectionDeleteDialogComponent,
    SnowflakeConnectionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleSnowflakeConnectionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
