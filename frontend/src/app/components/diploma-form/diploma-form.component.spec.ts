import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DiplomaFormComponent} from './diploma-form.component';

describe('DiplomaFormComponent', () => {
  let component: DiplomaFormComponent;
  let fixture: ComponentFixture<DiplomaFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [DiplomaFormComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiplomaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
