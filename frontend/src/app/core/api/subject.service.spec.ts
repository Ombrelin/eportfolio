import {TestBed} from '@angular/core/testing';

import {SubjectApiService} from './subject-api.service';

describe('SubjectService', () => {
  let service: SubjectApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
