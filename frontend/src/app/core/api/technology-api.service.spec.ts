import { TestBed } from '@angular/core/testing';

import { TechnologyApiService } from './technology-api.service';

describe('TechnologyApiService', () => {
  let service: TechnologyApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TechnologyApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
