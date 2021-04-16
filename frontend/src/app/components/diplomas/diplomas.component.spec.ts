import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DiplomasComponent} from './diplomas.component';

describe('DiplomasComponent', () => {
  let component: DiplomasComponent;
  let fixture: ComponentFixture<DiplomasComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DiplomasComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiplomasComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
