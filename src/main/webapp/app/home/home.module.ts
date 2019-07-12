import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JoyrideModule } from 'ngx-joyride';
import { SnowpoleSharedModule } from 'app/shared';
import { HOME_ROUTE, HomeComponent } from './';

@NgModule({
  imports: [SnowpoleSharedModule, RouterModule.forChild([HOME_ROUTE]), JoyrideModule.forRoot()],
  declarations: [HomeComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleHomeModule {}
