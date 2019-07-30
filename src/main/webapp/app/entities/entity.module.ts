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
        path: 'snow-ddl',
        loadChildren: './snow-ddl/snow-ddl.module#SnowpoleSnowDDLModule'
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
