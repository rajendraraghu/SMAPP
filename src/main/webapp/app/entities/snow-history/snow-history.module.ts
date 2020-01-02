import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SnowpoleSharedModule } from 'app/shared';
import {
  SnowHistoryComponent,
  SnowHistoryDetailComponent,
  SnowHistoryUpdateComponent,
  SnowHistoryDeletePopupComponent,
  SnowHistoryDeleteDialogComponent,
  snowHistoryRoute,
  snowHistoryPopupRoute
} from './';
import { JoyrideModule } from 'ngx-joyride';
import { SnowHistoryProcessStatusComponent } from './snow-history-process-status.component';
import { SnowHistoryJobStatusComponent } from './snow-history-job-status.component';

const ENTITY_STATES = [...snowHistoryRoute, ...snowHistoryPopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES), JoyrideModule.forChild()],
  declarations: [
    SnowHistoryComponent,
    SnowHistoryDetailComponent,
    SnowHistoryUpdateComponent,
    SnowHistoryDeleteDialogComponent,
    SnowHistoryDeletePopupComponent,
    SnowHistoryProcessStatusComponent,
    SnowHistoryJobStatusComponent
  ],
  entryComponents: [SnowHistoryComponent, SnowHistoryUpdateComponent, SnowHistoryDeleteDialogComponent, SnowHistoryDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleSnowHistoryModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
