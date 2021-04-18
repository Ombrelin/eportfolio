import { TestBed } from '@angular/core/testing';

import { SubjectService } from './subject-api.service';

describe('SubjectService', () => {
  let service: SubjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubjectService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
