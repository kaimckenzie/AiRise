using System.Text.Json.Serialization;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace AiRise.Models.User
{
    public class UserProgress
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string? Id { get; set; }

        [BsonElement("firebaseUid")]
        [JsonPropertyName("firebaseUid")]
        public string FirebaseUid { get; set; } = null!;

        [BsonElement("calories")]
        [JsonPropertyName("calories")]
        public int Calories { get; set; } = 0;

        [BsonElement("steps")]
        [JsonPropertyName("steps")]
        public int Steps { get; set; } = 0;

        [BsonElement("weight")]
        [JsonPropertyName("weight")]
        public int Weight { get; set; } = 0;

        [BsonElement("hydration")]
        [JsonPropertyName("hydration")]
        public int Hydration { get; set; } = 0;

        [BsonElement("sleep")]
        [JsonPropertyName("sleep")]
        public int Sleep { get; set; } = 0;
    }
}