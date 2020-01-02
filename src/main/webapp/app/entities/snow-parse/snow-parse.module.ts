import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { SnowpoleSharedModule } from 'app/shared';
import {
  SnowParseComponent,
  SnowParseUpdateComponent,
  SnowParseDeletePopupComponent,
  SnowParseDeleteDialogComponent,
  snowParseRoute,
  snowParsePopupRoute
} from './';
import { SnowParseProcessStatusComponent } from 'app/entities/snow-parse/snow-parse-process-status.component';
import { JoyrideModule } from 'ngx-joyride';
import { SnowParseJobStatusComponent } from './snow-parse-job-status.component';

const ENTITY_STATES = [...snowParseRoute, ...snowParsePopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES), JoyrideModule.forChild()],
  declarations: [
    SnowParseComponent,
    SnowParseUpdateComponent,
    SnowParseDeleteDialogComponent,
    SnowParseDeletePopupComponent,
    SnowParseProcessStatusComponent,
    SnowParseJobStatusComponent
  ],
  entryComponents: [SnowParseComponent, SnowParseUpdateComponent, SnowParseDeleteDialogComponent, SnowParseDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleSnowParseModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
