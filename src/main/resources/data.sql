-- User A: gillar mest MUSIC (rock/pop)
INSERT INTO history_entity (user_id, item_type, item_id, played_at) VALUES
                                                                        ('userA', 'MUSIC', 'song_rock_01', '2025-11-01T10:00:00Z'),
                                                                        ('userA', 'MUSIC', 'song_pop_01',  '2025-11-01T10:30:00Z'),
                                                                        ('userA', 'MUSIC', 'song_rock_02', '2025-11-01T11:00:00Z'),
                                                                        ('userA', 'MUSIC', 'song_rock_03', '2025-11-01T12:15:00Z'),
                                                                        ('userA', 'POD',   'pod_tech_01',  '2025-11-01T13:00:00Z'),
                                                                        ('userA', 'MUSIC', 'song_pop_02',  '2025-11-02T09:00:00Z'),
                                                                        ('userA', 'MUSIC', 'song_rock_04', '2025-11-02T10:00:00Z');

-- User B: blandat MUSIC + POD
INSERT INTO history_entity (user_id, item_type, item_id, played_at) VALUES
                                                                        ('userB', 'POD',   'pod_crime_01', '2025-11-01T08:00:00Z'),
                                                                        ('userB', 'POD',   'pod_crime_02', '2025-11-01T09:00:00Z'),
                                                                        ('userB', 'MUSIC', 'song_pop_03',  '2025-11-01T09:30:00Z'),
                                                                        ('userB', 'MUSIC', 'song_pop_04',  '2025-11-01T10:15:00Z'),
                                                                        ('userB', 'POD',   'pod_tech_02',  '2025-11-01T11:45:00Z');

-- User C: mest VIDEO
INSERT INTO history_entity (user_id, item_type, item_id, played_at) VALUES
                                                                        ('userC', 'VIDEO', 'video_coding_01', '2025-11-01T07:30:00Z'),
                                                                        ('userC', 'VIDEO', 'video_coding_02', '2025-11-01T08:15:00Z'),
                                                                        ('userC', 'VIDEO', 'video_music_01',  '2025-11-01T09:45:00Z'),
                                                                        ('userC', 'MUSIC', 'song_ambient_01', '2025-11-01T10:30:00Z');

-- User D: lite blandat, bra för test av rekommendationer
INSERT INTO history_entity (user_id, item_type, item_id, played_at) VALUES
                                                                        ('userD', 'MUSIC', 'song_rock_05',  '2025-11-01T12:00:00Z'),
                                                                        ('userD', 'MUSIC', 'song_rock_06',  '2025-11-01T12:30:00Z'),
                                                                        ('userD', 'POD',   'pod_history_01','2025-11-01T13:15:00Z'),
                                                                        ('userD', 'VIDEO', 'video_funny_01','2025-11-01T14:00:00Z');