import {TestBed} from '@angular/core/testing';

import {AbilityApiService} from './ability-api.service';

describe('AbilityApiService', () => {
  let service: AbilityApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AbilityApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
