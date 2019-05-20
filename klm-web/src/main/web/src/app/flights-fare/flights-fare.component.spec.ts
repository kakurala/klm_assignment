import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FlightsFareComponent } from './flights-fare.component';

describe('FlightsFareComponent', () => {
  let component: FlightsFareComponent;
  let fixture: ComponentFixture<FlightsFareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FlightsFareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FlightsFareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
