import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TestRunDetailsComponent } from './test-run-details.component';

describe('TestRunDetailsComponent', () => {
  let component: TestRunDetailsComponent;
  let fixture: ComponentFixture<TestRunDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TestRunDetailsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TestRunDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
