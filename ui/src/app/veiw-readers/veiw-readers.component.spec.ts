import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VeiwReadersComponent } from './veiw-readers.component';

describe('VeiwReadersComponent', () => {
  let component: VeiwReadersComponent;
  let fixture: ComponentFixture<VeiwReadersComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VeiwReadersComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VeiwReadersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
