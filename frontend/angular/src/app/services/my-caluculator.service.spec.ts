import { TestBed } from '@angular/core/testing';

import { MyCaluculatorService } from './my-caluculator.service';

describe('MyCaluculatorService', () => {
  let service: MyCaluculatorService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MyCaluculatorService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
