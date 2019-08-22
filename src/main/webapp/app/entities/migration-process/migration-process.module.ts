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
  MigrationProcessPrimaryKeyComponent,
  migrationProcessRoute,
  migrationProcessPopupRoute,
  MigrationProcessPrimaryKeyPopupComponent
} from './';
import { JoyrideModule } from 'ngx-joyride';
import { MigrationProcessStatusComponent } from './migration-process-status.component';
import { MigrationProcessJobStatusComponent } from './migration-process-job-status.component';

const ENTITY_STATES = [...migrationProcessRoute, ...migrationProcessPopupRoute];

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild(ENTITY_STATES), JoyrideModule.forChild()],
  declarations: [
    MigrationProcessComponent,
    MigrationProcessDetailComponent,
    MigrationProcessUpdateComponent,
    MigrationProcessDeleteDialogComponent,
    MigrationProcessDeletePopupComponent,
    MigrationProcessStatusComponent,
    MigrationProcessJobStatusComponent,
    MigrationProcessPrimaryKeyComponent,
    MigrationProcessPrimaryKeyPopupComponent
  ],
  entryComponents: [
    MigrationProcessComponent,
    MigrationProcessUpdateComponent,
    MigrationProcessDeleteDialogComponent,
    MigrationProcessDeletePopupComponent,
    MigrationProcessPrimaryKeyPopupComponent,
    MigrationProcessPrimaryKeyComponent
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
