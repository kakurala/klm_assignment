import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightsSelectorComponent } from './flights-selector.component';

describe('FlightsSelectorComponent', () => {
  let component: FlightsSelectorComponent;
  let fixture: ComponentFixture<FlightsSelectorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightsSelectorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightsSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
