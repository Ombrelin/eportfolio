import {TestBed} from '@angular/core/testing';

import {DiplomaApiService} from './diploma-api.service';

describe('DiplomaApiService', () => {
  let service: DiplomaApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DiplomaApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
