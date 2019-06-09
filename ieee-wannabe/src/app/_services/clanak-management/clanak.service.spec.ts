import { TestBed } from '@angular/core/testing';

import { ClanakService } from './clanak.service';

describe('ClanakService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ClanakService = TestBed.get(ClanakService);
    expect(service).toBeTruthy();
  });
});
