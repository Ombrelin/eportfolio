import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {AbilityApiService, createAbilityApi} from "./api/ability-api.service";
import {AuthService} from "./services/auth.service";
import {AuthApiService, createAuthApi} from "./api/auth-api.service";


@NgModule({
    imports: [
      CommonModule
    ],
    providers:
      [{provide: AbilityApiService, useValue: createAbilityApi()}, {provide: AuthApiService, useValue: createAuthApi()}]
  }
)
export class CoreModule {
}
