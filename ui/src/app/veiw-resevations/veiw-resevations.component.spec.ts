import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { VeiwResevationsComponent } from './veiw-resevations.component';

describe('VeiwResevationsComponent', () => {
  let component: VeiwResevationsComponent;
  let fixture: ComponentFixture<VeiwResevationsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ VeiwResevationsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(VeiwResevationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
