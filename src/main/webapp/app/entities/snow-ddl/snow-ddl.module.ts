import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SnowpoleSharedModule } from 'app/shared';
import {
  SnowDDLComponent,
  SnowDDLUpdateComponent,
  SnowDDLDeletePopupComponent,
  SnowDDLDeleteDialogComponent,
  snowDDLRoute,
  snowDDLPopupRoute
} from './';
import { ReportComponent } from 'app/entities/snow-ddl/report.component';
import { JoyrideModule } from 'ngx-joyride';

const ENTITY_STATES = [...snowDDLRoute, ...snowDDLPopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES), JoyrideModule.forChild()],
  declarations: [SnowDDLComponent, SnowDDLUpdateComponent, SnowDDLDeleteDialogComponent, SnowDDLDeletePopupComponent, ReportComponent],
  entryComponents: [SnowDDLComponent, SnowDDLUpdateComponent, SnowDDLDeleteDialogComponent, SnowDDLDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleSnowDDLModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
