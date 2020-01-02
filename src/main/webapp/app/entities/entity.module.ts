import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'source-connection',
        loadChildren: './source-connection/source-connection.module#SnowpoleSourceConnectionModule'
      },
      {
        path: 'snowflake-connection',
        loadChildren: './snowflake-connection/snowflake-connection.module#SnowpoleSnowflakeConnectionModule'
      },
      {
        path: 'migration-process',
        loadChildren: './migration-process/migration-process.module#SnowpoleMigrationProcessModule'
      },
      {
        path: 'delta-process',
        loadChildren: './delta-process/delta-process.module#SnowpoleDeltaProcessModule'
      },
      {
        path: 'snow-ddl',
        loadChildren: './snow-ddl/snow-ddl.module#SnowpoleSnowDDLModule'
      },
      {
        path: 'snow-history',
        loadChildren: './snow-history/snow-history.module#SnowpoleSnowHistoryModule'
      },
      {
        path: 'snow-parse',
        loadChildren: './snow-parse/snow-parse.module#SnowpoleSnowParseModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleEntityModule {}
