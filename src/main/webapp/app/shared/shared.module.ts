import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { SnowpoleSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [SnowpoleSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [SnowpoleSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SnowpoleSharedModule {
  static forRoot() {
    return {
      ngModule: SnowpoleSharedModule
    };
  }
}
