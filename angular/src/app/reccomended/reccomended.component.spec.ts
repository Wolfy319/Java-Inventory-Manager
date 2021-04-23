import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReccomendedComponent } from './reccomended.component';

describe('ReccomendedComponent', () => {
  let component: ReccomendedComponent;
  let fixture: ComponentFixture<ReccomendedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReccomendedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReccomendedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
