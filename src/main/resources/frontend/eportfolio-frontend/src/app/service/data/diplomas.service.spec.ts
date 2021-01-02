import {TestBed} from '@angular/core/testing';

import {DiplomasService} from './diplomas.service';

describe('DiplomasService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: DiplomasService = TestBed.get(DiplomasService);
    expect(service).toBeTruthy();
  });
});
