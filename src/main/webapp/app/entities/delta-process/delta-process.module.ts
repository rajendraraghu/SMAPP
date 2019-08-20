import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SnowpoleSharedModule } from 'app/shared';
import {
  DeltaProcessComponent,
  DeltaProcessDetailComponent,
  DeltaProcessUpdateComponent,
  DeltaProcessDeletePopupComponent,
  DeltaProcessDeleteDialogComponent,
  deltaProcessRoute,
  deltaProcessPopupRoute
} from './';
import { JoyrideModule } from 'ngx-joyride';
import { DeltaProcessStatusComponent } from './delta-process-status.component';
import { DeltaProcessJobStatusComponent } from './delta-process-job-status.component';

const ENTITY_STATES = [...deltaProcessRoute, ...deltaProcessPopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES), JoyrideModule.forChild()],
  declarations: [
    DeltaProcessComponent,
    DeltaProcessDetailComponent,
    DeltaProcessUpdateComponent,
    DeltaProcessDeleteDialogComponent,
    DeltaProcessDeletePopupComponent,
    DeltaProcessStatusComponent,
    DeltaProcessJobStatusComponent
  ],
  entryComponents: [
    DeltaProcessComponent,
    DeltaProcessUpdateComponent,
    DeltaProcessDeleteDialogComponent,
    DeltaProcessDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleDeltaProcessModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
