import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewerArticlesComponent } from './reviewer-articles.component';

describe('ReviewerArticlesComponent', () => {
  let component: ReviewerArticlesComponent;
  let fixture: ComponentFixture<ReviewerArticlesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewerArticlesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewerArticlesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
