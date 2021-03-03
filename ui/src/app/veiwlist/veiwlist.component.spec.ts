import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VeiwlistComponent } from './veiwlist.component';

describe('VeiwlistComponent', () => {
  let component: VeiwlistComponent;
  let fixture: ComponentFixture<VeiwlistComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VeiwlistComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VeiwlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
