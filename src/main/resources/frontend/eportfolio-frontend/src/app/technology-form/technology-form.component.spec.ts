import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {TechnologyFormComponent} from './technology-form.component';

describe('TechnologyFormComponent', () => {
  let component: TechnologyFormComponent;
  let fixture: ComponentFixture<TechnologyFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [TechnologyFormComponent]
    })
      .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TechnologyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
