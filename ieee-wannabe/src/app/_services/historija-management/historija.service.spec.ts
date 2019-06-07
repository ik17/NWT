import { TestBed } from '@angular/core/testing';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment.prod';
import { HistorijaService } from './historija.service';

const baseUrl = environment.url + '/historijaUI/';

describe('HistorijaService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: HistorijaService = TestBed.get(HistorijaService);
    expect(service).toBeTruthy();
  });
});
