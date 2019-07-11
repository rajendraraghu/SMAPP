import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { SnowpoleSharedModule } from 'app/shared';
import {
  MigrationProcessComponent,
  MigrationProcessDetailComponent,
  MigrationProcessUpdateComponent,
  MigrationProcessDeletePopupComponent,
  MigrationProcessDeleteDialogComponent,
  migrationProcessRoute,
  migrationProcessPopupRoute
} from './';
import { ReportComponent } from 'app/entities/migration-process/report.component';

const ENTITY_STATES = [...migrationProcessRoute, ...migrationProcessPopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    MigrationProcessComponent,
    MigrationProcessDetailComponent,
    MigrationProcessUpdateComponent,
    MigrationProcessDeleteDialogComponent,
    MigrationProcessDeletePopupComponent,
    ReportComponent
  ],
  entryComponents: [
    MigrationProcessComponent,
    MigrationProcessUpdateComponent,
    MigrationProcessDeleteDialogComponent,
    MigrationProcessDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleMigrationProcessModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
